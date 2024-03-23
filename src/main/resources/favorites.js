document.addEventListener("DOMContentLoaded", () => loadPage())

function loadPage() {
    const displayBox = document.querySelector('#display')
    displayBox.innerHTML = ""

    let html = ""
    let recipeNumber = 1
    //Definite For Loops for proof of concept. This needs to be updated
    for(let i = 0; i < 3; i++) {
        html += `<div class="row py-3">`
        for(let j = 0; j < 4; j++) {
            html += `
            <div class="col mx-auto">
                <div class="card p-2" style=width:18rem;>
                    <div class="card-body d-flex justify-content-between">
                        <h5 class="card-title mb-0">Recipe${recipeNumber}</h5>
                        <svg class="star my-auto" "xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                        </svg>
                    </div>
                    <img src="kid.jpg" class="card-img">
                </div>
            </div>
            `
            recipeNumber++
        }
        html += "</div>"
    }
    displayBox.innerHTML = html

}