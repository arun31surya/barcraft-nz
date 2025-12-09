document.addEventListener("DOMContentLoaded", function () {
  const lightbox = document.getElementById("lightbox");
  const lightboxImg = document.getElementById("lightbox-img");
  const closeBtn = document.querySelector(".lightbox-close");

  document.querySelectorAll(".gallery-item img").forEach(img => {
    img.addEventListener("click", () => {
      lightboxImg.src = img.src;
      lightbox.classList.add("is-visible");
    });
  });

  function hideLightbox() {
    lightbox.classList.remove("is-visible");
    lightboxImg.src = "";
  }

  closeBtn.addEventListener("click", hideLightbox);

  lightbox.addEventListener("click", e => {
    if (e.target === lightbox) hideLightbox();
  });

  document.addEventListener("keyup", e => {
    if (e.key === "Escape") hideLightbox();
  });
});
