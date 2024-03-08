document.addEventListener('DOMContentLoaded', () => {
    document.querySelector('#red').addEventListener('click', () => {
        fetch("https://api.spoonacular.com/recipes/716429/information?apiKey=337e21a72943435b8eb4128acc08555d")
        .then(recipe => recipe.json())
        .then(data => {
            console.log(data.title)
            document.querySelector('#message').innerHTML = `${data.title}`
        })
    })
})