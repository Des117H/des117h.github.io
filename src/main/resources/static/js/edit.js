
let toolbaroptions = [
    [""]
]

let quill = new Quill("#editor",{
    modules: {
        toolbar: true,
    },
    theme: "snow"

})


let fileID = "561f885c-d794-4985-b04d-b549eb993758";
fetch('/document/contents/' + fileID)
  .then(response => response.text())
  .then(data => {
    // Access the Quill editor instance
    console.log(data);
    quill.setText(data);
});

