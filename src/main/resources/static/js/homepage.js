const form = document.getElementById('file-upload-form');
window.onload = (event) => {
    getDocumentsList();
  };

form.addEventListener('submit', async (event) => {
    event.preventDefault();
    // Show loading modal
    $('#loading-modal').modal('show');
    const file = document.getElementById('file-input').files[0];
    const user = document.getElementById('user-id').value;
    if (!file) {
        return;
    }

    var d = new Date();
    var dateTime = d.getFullYear() + "-"
        + (d.getMonth() + 1) + "-"
        + d.getDate() + "_"
        + d.getHours() + ":"
        + d.getMinutes() + ":"
        + d.getSeconds();

    const formData = new FormData();
    formData.append("file", file);
    formData.append("user-id", user + "");
    formData.append("uploaded-at", dateTime + "");

    const response = await fetch("/document/upload/", {
        method: "POST",
        body: formData,
    })
        .then((response) => response.json())
        .then((data) => {
            console.log('File uploaded successfully');
            console.log(data);
        })
        .catch((error) => {
            console.error(formData);
            console.error('Upload failed:', error);
        });

    console.log(response);

    uploadProgress(100);
});

function uploadProgress(time) {
    // Simulate upload progress
    const progressBar = $('.progress-bar');
    let progress = 0;
    const interval = setInterval(() => {
        progress += 10;
        if (progress === time) {
            clearInterval(interval);
            $('#loading-modal').modal('hide');
            // Reset form
            document.getElementById('file-upload-form').reset();
        }
        progressBar.attr('aria-valuenow', progress);
        progressBar.css('width', `${progress}%`);
    }, time); // Change interval to 1000 for 1-second updates

    // getDocumentsList("0186e0bf-2e30-4d1d-b23b-f72bf7520fbc");
}

function getDocumentsList() {
    document.getElementById('doc-template').innerHTML = "";

    fetch('/get/documentsList/' + "0186e0bf-2e30-4d1d-b23b-f72bf7520fbc", {
        method: 'GET'
    })
        .then((response) => response.json())
        .then((data) => {
            getDocumentMetadata(Object.keys(data))
        })
        .catch((error) => {
            console.error('Upload failed:', error);
        });
}

function getDocumentMetadata(docList) {
    for (let i = 0; i < docList.length; i++) {
        fetch("/document/get-metadata/" + docList[i], {
            method: "GET"
        })
            .then((response) => response.json())
            .then((data) => {
                showDocument(data);
            })
    }
}

function showDocument(documentData) {
    const nameArray = documentData.filename.split("|");

    const docTemplate = document.getElementById('doc-template');
	const docContainer = document.getElementById('document-container');

	const templateClone = docTemplate.content.cloneNode(true);
    
	templateClone.querySelector(".doc-name").innerHTML = nameArray[1] + nameArray[3];
	templateClone.querySelector(".doc-date").innerHTML = documentData.uploadedAt.replaceAll("-", "/").replace("_", " ");

    docContainer.appendChild(templateClone);
}