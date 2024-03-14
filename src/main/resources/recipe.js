document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const recipeName = urlParams.get('name');
    const recipeID = urlParams.get(`id`);
    document.querySelector('#recipeName').textContent = decodeURIComponent(recipeName);
    document.querySelector('#recipeID').textContent = decodeURIComponent(recipeID);
})
