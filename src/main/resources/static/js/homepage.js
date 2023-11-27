const form = document.getElementById('file-upload-form');
form.addEventListener('submit', (event) => {
    event.preventDefault();

    const file = document.getElementById('file-input').files[0];
    if (!file) {
        return;
    }

    const formData = new FormData();
    formData.append('file', file);
    
    console.log(file)

    fetch('/upload', {
        method: 'POST',
        body: formData
    })
    .then((response) => response.json())
    .then((data) => {
        console.log('File uploaded successfully');
        // Handle successful upload
    })
    .catch((error) => {
        console.error('Upload failed:', error);
    });
});