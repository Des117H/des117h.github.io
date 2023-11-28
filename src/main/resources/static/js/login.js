const form = document.getElementById('userForm');

form.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new FormData(form);

    fetch('/submitUser', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        console.log('User submitted:', data);
    })
    .catch(error => {
        console.error('Error submitting user:', error);
    });
});
