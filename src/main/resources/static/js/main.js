document.addEventListener("DOMContentLoaded", function () {

  // SERVICES DROPDOWN
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

    document.addEventListener("click", (e) => {
      if (!menu.classList.contains("is-open")) return;
      if (toggle.contains(e.target) || menu.contains(e.target)) return;
      closeMenu();
    });

    document.addEventListener("keyup", (e) => {
      if (e.key === "Escape") closeMenu();
    });
  })();


  // GALLERY LIGHTBOX
  (function initLightbox() {
    const lightbox = document.getElementById("lightbox");
    const lightboxImg = document.getElementById("lightbox-img");
    const closeBtn = document.querySelector(".lightbox-close");

    if (!lightbox || !lightboxImg) return;

    function hideLightbox() {
      lightbox.classList.remove("is-visible");
      lightboxImg.src = "";
    }

    document.querySelectorAll(".gallery-item img").forEach((img) => {
      img.addEventListener("click", () => {
        lightboxImg.src = img.src;
        lightbox.classList.add("is-visible");
      });
    });

    if (closeBtn) closeBtn.addEventListener("click", hideLightbox);

    lightbox.addEventListener("click", (e) => {
      if (e.target === lightbox) hideLightbox();
    });

    document.addEventListener("keyup", (e) => {
      if (e.key === "Escape") hideLightbox();
    });
  })();


  // FLATPICKR
  (function initFlatpickr() {
    const dateInputs = document.querySelectorAll(".js-date");
    if (!dateInputs.length) return;

    if (typeof flatpickr === "undefined") {
      console.warn("flatpickr is not loaded.");
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


  // GUEST PRICE
  (function initAutoPricing() {
    const guestsSelect =
      document.getElementById("guestsSelect") ||
      document.getElementById("guestsSelectEl") ||
      document.querySelector("select[name='guests']");

    const priceValueEl = document.getElementById("priceEstimateValue");
    const priceNoteEl = document.getElementById("priceEstimateNote");

    if (!guestsSelect || !priceValueEl || !priceNoteEl) return;

    const pricing = {
      "0-20": { from: 280, note: "Typical for smaller home events. Add hours + travel if outside Auckland." },
      "20-40": { from: 350, note: "Common for birthdays and house parties. Add hours + travel." },
      "40-60": { from: 450, note: "Often needs faster service flow and more prep time." },
      "60+": { from: 550, note: "Larger events may need extra setup time or an extra bartender." }
    };

    function updateEstimate() {
      const key = guestsSelect.value;

      if (!key || !pricing[key]) {
        priceValueEl.textContent = "Select guest count to see estimate";
        priceNoteEl.textContent =
          "BYO alcohol. Glassware available. Final quote depends on hours, menu, and travel.";
        return;
      }

      const p = pricing[key];
      priceValueEl.textContent = "From approx. $" + p.from + " + hourly";
      priceNoteEl.textContent = "BYO alcohol. Glassware available. " + p.note;
    }

    guestsSelect.addEventListener("change", updateEstimate);
    updateEstimate();
  })();

});
document.addEventListener("DOMContentLoaded", () => {

  // MOBILE NAV TOGGLE
  const mobileToggle = document.querySelector(".mobile-nav-toggle");
  const nav = document.querySelector(".nav");

  if (mobileToggle && nav) {
    mobileToggle.addEventListener("click", (e) => {
      e.stopPropagation();
      nav.classList.toggle("is-open");
      mobileToggle.setAttribute(
        "aria-expanded",
        nav.classList.contains("is-open")
      );
    });
  }

  // SERVICES DROPDOWN
  const servicesToggle = document.querySelector(".services-toggle");
  const servicesDropdown = document.getElementById("servicesDropdown");

  if (servicesToggle && servicesDropdown) {
    servicesToggle.addEventListener("click", (e) => {
      e.stopPropagation();
      servicesDropdown.classList.toggle("is-open");
    });

    document.addEventListener("click", () => {
      servicesDropdown.classList.remove("is-open");
    });
  }

});