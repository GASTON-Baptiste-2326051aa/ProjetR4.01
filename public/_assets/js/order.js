document.addEventListener("DOMContentLoaded", function () {
    const validateForm = document.querySelector("form[action*='validate']");
    if (validateForm) {
        validateForm.addEventListener("submit", function (event) {
            event.preventDefault(); // Empêche l'envoi immédiat du formulaire
            if (confirm("Êtes-vous sûr de vouloir enregistrer votre commande ?")) {
                this.submit(); // Envoie le formulaire si l'utilisateur confirme
            }
        });
    }
});
