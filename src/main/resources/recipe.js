document.addEventListener('DOMContentLoaded', async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const recipeName = urlParams.get('name');
    const recipeID = urlParams.get('id');
    const recipeImage = urlParams.get('image');

    // Fetch the recipe information
    const response = await fetch(`https://api.spoonacular.com/recipes/${recipeID}/information?apiKey=337e21a72943435b8eb4128acc08555d`);
    const recipeInfo = await response.json();
    console.log(recipeInfo)

    // Display the recipe information
    document.querySelector('#recipeName').textContent = decodeURIComponent(recipeName);
    document.querySelector('#recipeImage').src = decodeURIComponent(recipeImage);

    // Create a mapping between the display names and the actual property names
    const tabs = {
        'Cuisine': 'cuisines',
        'Diet': 'diets',
        'Dish Type': 'dishTypes',
        'Source': 'sourceUrl',
        'Servings': 'servings',
        'Health Score': 'healthScore',
        'Gluten Free': 'glutenFree',
        'Food ID': 'id',
        'Ready In Minutes': 'readyInMinutes',
    };
    

    let html = '';
    let index = 0;
    for (const displayName in tabs) {
        if (index % 3 === 0) { // if index is divisible by 3 (start of a new row)
            html += '<div class="row">'; // start a new row
        }
        const propName = tabs[displayName];
        let propValue = recipeInfo[propName];
        // Convert boolean values to 'Yes' or 'No'
        if (typeof propValue === 'boolean') {
            propValue = propValue ? 'Yes' : 'No';
        }
        // Create a hyperlink for the 'Source' tab
        if (displayName === 'Source') {
            propValue = `<a href="${propValue}">${recipeInfo.creditsText}</a>`;
        }
        html += `
        <div class="col-4">
            <div class="card mt-4">
                <div class="card-header">${displayName}</div>
                <div class="card-body">${propValue}</div>
            </div>
        </div>`;
        if (index % 3 === 2) { // if index is divisible by 3 with a remainder of 2 (end of a row)
            html += '</div>'; // end the row
        }
        index++;
    }
    if (index % 3 !== 0) { // if the last row has less than 3 tabs
        html += '</div>'; // end the last row
    }

    // Add the summary and ingredients in two columns
    html += `<div class="row">
                <div class="col-6">
                    <div class="card mt-4">
                        <div class="card-header">Summary</div>
                        <div class="card-body">${recipeInfo.summary}</div>
                    </div>
                </div>
                <div class="col-6">
                    <div class="card mt-4">
                        <div class="card-header">Ingredients</div>
                        <div class="card-body">
                            <ul style="list-style-type: none;">`;
    recipeInfo.extendedIngredients.forEach(ingredient => {
        html += `<li>${ingredient.amount} ${ingredient.unit} ${ingredient.name}</li>`;
    });
    html += `           </ul>
                        </div>
                    </div>
                </div>
            </div>`;

    document.querySelector('#recipeTabs').innerHTML = html;
})