document.addEventListener('DOMContentLoaded', function () {
  var swiper = new Swiper(".mySwiper", {
    // Configurações do Swiper
    cssMode: true,
    loop: true,
    navigation: {
      nextEl: ".swiper-button-next",
      prevEl: ".swiper-button-prev",
    },
    pagination: {
      el: ".swiper-pagination",
    },
    mousewheel: true,
    keyboard: true,
  });

  // Código para rolar suavemente ao clicar nos links do menu e centralizar o conteúdo da seção
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();

      const target = document.querySelector(this.getAttribute('href'));
      if (target) {
        let yOffset = -50; // Valor padrão para seções desconhecidas

        // Ajuste de deslocamento específico para cada seção
        if (target.id === 'about') {
          yOffset = -100; // Ajuste para a seção Sobre
        } else if (target.id === 'services') {
          yOffset = -100; // Ajuste para a seção Serviços
        } else if (target.id === 'news') {
          yOffset = -300; // Ajuste para a seção Novidades
        } else if (target.id === 'contact') {
          yOffset = 100; // Ajuste para a seção Contato
        }

        const y = target.getBoundingClientRect().top + window.pageYOffset + yOffset;

        window.scrollTo({
          top: y,
          behavior: 'smooth'
        });
      }
    });
  });
});