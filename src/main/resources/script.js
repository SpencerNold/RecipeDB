//Waits for the page to load, then when the button is clicked run the search function
document.addEventListener('DOMContentLoaded', () => {
    document.querySelector('#submitButton').addEventListener('click', () => search())
})


async function search() {
    const searchInput = document.querySelector('#searchBox').value
    const resultsBox = document.querySelector('#results')

    // Gets data from api and convert it to JSON
    const response = await fetch(`https://api.spoonacular.com/recipes/complexSearch?apiKey=337e21a72943435b8eb4128acc08555d&query=${searchInput}&number=10`)
    const responseJson = await response.json()
    
    // Reset the results box and displays the new results that were just searched for
    resultsBox.innerHTML = ''
    responseJson.results.forEach(recipe => {
        resultsBox.innerHTML += `<li>${recipe.title}</li>`
    })
}