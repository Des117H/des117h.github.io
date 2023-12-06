

let toolbaroptions = [
    [""]
]

let quill = new Quill("#editor",{
    modules: {
        toolbar: true,
    },
    theme: "snow"

})


let fileID = "02ab6313-857c-4a5f-a4b4-b3c842eced48";
fetch('/document/get-metadata/' + fileID)
  .then(response => response.text())
  .then(data => {
    quill.setText(data);
});

function submitText() {
    // Get the text from the textarea
    let textToSubmit = document.getElementById('customTextArea').value;

    // Construct the API request URL
    const apiUrl = `https://3c92-103-253-89-37.ngrok-free.app/generate_code?prompts=${encodeURIComponent(textToSubmit)}`;

    // Make the API request
    fetch(apiUrl)
        .then(response => response.json())
        .then(generatedCodeList => {
            // Display the generated code in the result div
            const resultDiv = document.getElementById('result');
            resultDiv.innerHTML = '';

            generatedCodeList.forEach((code, index) => {
                const codeElement = document.createElement('div');
                codeElement.innerHTML = `${code}`;
                resultDiv.appendChild(codeElement);
            });
        })
        .catch(error => {
            // Handle errors
            console.error('Error:', error);
        });
}
//https://viblo.asia/p/cach-khac-phuc-loi-cors-policy-khi-goi-api-tu-frontend-gDVK23aeZLj
// open -n -a /Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --args --user-data-dir="/tmp/chrome_dev_test" --disable-web-security

