//Waits for the page to load, then when the button is clicked run the search function
document.addEventListener('DOMContentLoaded', () => {
    document.querySelector('#submitButton').addEventListener('click', () => search())
})

async function search() {
    const searchInput = document.querySelector('#searchBox').value
    const resultsBox = document.querySelector('#results')

    // Gets data from api and convert it to JSON
    const response = await fetch(`https://api.spoonacular.com/recipes/complexSearch?apiKey=337e21a72943435b8eb4128acc08555d&query=${searchInput}&number=9`)
    const responseJson = await response.json()
    // Reset the results box and displays the new results that were just searched for
    resultsBox.innerHTML = ''
    let html = '';
    responseJson.results.forEach((recipe, index) => {
        if (index % 3 === 0) { // if index is divisible by 3 (start of a new row)
            html += '<div class="row">'; // start a new row
        }
        console.log(recipe.id)
        html += `
        <div class="col-4">
        <a href="recipe.html?name=${encodeURIComponent(recipe.title)}&id=${encodeURIComponent(recipe.id)}" class="text-decoration-none text-dark">
        <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">${recipe.title}</h5>
                    </div>
                </div>
            </a>
        </div>`
        if (index % 3 === 2) { // if index is divisible by 3 with a remainder of 2 (end of a row)
            html += '</div>'; // end the row
        }
    })
    resultsBox.innerHTML = html;
}