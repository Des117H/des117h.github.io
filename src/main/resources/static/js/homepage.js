const form = document.getElementById('file-upload-form');
form.addEventListener('submit', (event) => {
    event.preventDefault();
    // Show loading modal
    $('#loading-modal').modal('show');
    const file = document.getElementById('file-input').files[0];
    if (!file) {
        return;
    }

    const formData = new FormData();
    formData.append('file', file);
    
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

    
  
     // Simulate upload progress
     const progressBar = $('.progress-bar');
     let progress = 0;
     const interval = setInterval(() => {
       progress += 10;
       if (progress === 100) {
         clearInterval(interval);
         $('#loading-modal').modal('hide');
         // Reset form
         document.getElementById('file-upload-form').reset();
       }
       progressBar.attr('aria-valuenow', progress);
       progressBar.css('width', `${progress}%`);
     }, 100); // Change interval to 1000 for 1-second updates
});
