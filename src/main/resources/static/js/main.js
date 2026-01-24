document.addEventListener("DOMContentLoaded", function () {

// =========================
// SERVICES DROPDOWN TOGGLE
// =========================
(function initServicesDropdown() {
  const toggle = document.querySelector(".services-toggle");
  const menu = document.getElementById("servicesDropdown");
  if (!toggle || !menu) return;

  function closeMenu() {
    menu.classList.remove("is-open");
    toggle.setAttribute("aria-expanded", "false");
  }

  toggle.addEventListener("click", (e) => {
    e.preventDefault();
    const isOpen = menu.classList.toggle("is-open");
    toggle.setAttribute("aria-expanded", String(isOpen));
  });

  // close on click outside
  document.addEventListener("click", (e) => {
    if (!menu.classList.contains("is-open")) return;
    if (toggle.contains(e.target) || menu.contains(e.target)) return;
    closeMenu();
  });

  // close on ESC
  document.addEventListener("keyup", (e) => {
    if (e.key === "Escape") closeMenu();
  });
})();

  // =========================
  // 1) GALLERY LIGHTBOX (safe)
  // =========================
  (function initLightbox() {
    const lightbox = document.getElementById("lightbox");
    const lightboxImg = document.getElementById("lightbox-img");
    const closeBtn = document.querySelector(".lightbox-close");

    if (!lightbox || !lightboxImg) return;

    function hideLightbox() {
      lightbox.classList.remove("is-visible");
      lightboxImg.src = "";
    }

    document.querySelectorAll(".gallery-item img").forEach(img => {
      img.addEventListener("click", () => {
        lightboxImg.src = img.src;
        lightbox.classList.add("is-visible");
      });
    });

    if (closeBtn) closeBtn.addEventListener("click", hideLightbox);

    lightbox.addEventListener("click", e => {
      if (e.target === lightbox) hideLightbox();
    });

    document.addEventListener("keyup", e => {
      if (e.key === "Escape") hideLightbox();
    });
  })();


  // =========================
  // 2) FLATPICKR (calendar)
  // =========================
  (function initFlatpickr() {
    const dateInputs = document.querySelectorAll(".js-date");
    if (!dateInputs.length) return;

    if (typeof flatpickr === "undefined") {
      console.warn("flatpickr is not loaded. Check your script import.");
      return;
    }

    dateInputs.forEach((el) => {
      flatpickr(el, {
        dateFormat: "d/m/Y",
        minDate: "today",
        disableMobile: true
      });
    });
  })();


  // =========================
  // 3) GUEST COUNT AUTO PRICE
  // =========================
  (function initAutoPricing() {
    const guestsSelect =
      document.getElementById("guestsSelect") ||
      document.getElementById("guestsSelectEl") ||
      document.querySelector("select[name='guests']");

    const priceValueEl = document.getElementById("priceEstimateValue");
    const priceNoteEl = document.getElementById("priceEstimateNote");

    // IMPORTANT: do not "return" from the whole file, only from this feature
    if (!guestsSelect || !priceValueEl || !priceNoteEl) return;

    const pricing = {
      "0-20": { from: 280, note: "Typical for smaller home events. Add hours + travel if outside Auckland." },
      "20-40": { from: 350, note: "Common for birthdays and house parties. Add hours + travel." },
      "40-60": { from: 450, note: "Often needs faster service flow and more prep time." },
      "60+": { from: 550, note: "Larger events may need extra setup time or an extra bartender." }
    };

    function formatNZD(amount) {
      return "$" + amount;
    }

    function updateEstimate() {
      const key = guestsSelect.value;

      if (!key || !pricing[key]) {
        priceValueEl.textContent = "Select guest count to see estimate";
        priceNoteEl.textContent =
          "BYO alcohol. Glassware available. Final quote depends on hours, menu, and travel.";
        return;
      }

      const p = pricing[key];
      priceValueEl.textContent =
        "From approx. " + formatNZD(p.from) + " + hourly";

      priceNoteEl.textContent =
        "BYO alcohol. Glassware available. " + p.note;
    }

    guestsSelect.addEventListener("change", updateEstimate);
    updateEstimate();
  })();


  // =========================
  // 4) AJAX ENQUIRY SUBMIT (fallback safe)
  // =========================
  (function initAjaxSubmit() {
    const form = document.getElementById("enquiryForm");
    if (!form) return;

    // If fetch isn't supported, do nothing (normal submit works)
    if (!window.fetch) return;

    const statusEl = document.getElementById("enquiryStatus");
    const btn = form.querySelector("button[type='submit'], input[type='submit']");

    form.addEventListener("submit", async function (e) {
      // Try AJAX, but keep normal submit as fallback
      e.preventDefault();

      if (btn) btn.disabled = true;
      if (statusEl) statusEl.textContent = "Sending…";

      try {
        const fd = new FormData(form);
        const payload = Object.fromEntries(fd.entries());

        const res = await fetch("/api/enquiry", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload),
        });

        if (!res.ok) throw new Error("API failed: " + res.status);

        // ✅ stay on same page (no redirect)
        if (statusEl) statusEl.textContent = "✅ Thanks. We'll reply shortly.";
        form.reset();
        if (btn) btn.disabled = false;

      } catch (err) {
        console.error("AJAX failed, falling back to normal submit:", err);
        if (statusEl) statusEl.textContent = "Network issue. Submitting normally…";
        if (btn) btn.disabled = false;

        // fallback normal submit to /enquiry (your existing controller)
        form.submit();
      }
    });
  })();

});