function modal(event) {
    // Button that triggered the modal
    var button = event.relatedTarget
    // Extract info from data-bs-* attributes
    var classApartment = button.getAttribute('data-bs-ca')
    // Update the modal's content.
    var modalTitle = exampleModal.querySelector('.modal-title')
    modalTitle.textContent = 'We suggest class apartment: ' + classApartment

}