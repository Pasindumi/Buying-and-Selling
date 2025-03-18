// Image slider for the left side ads
let currentAdIndex = 0;
const ads = document.querySelectorAll("#adContainer img");

function switchAd() {
    ads[currentAdIndex].style.display = "none";
    currentAdIndex = (currentAdIndex + 1) % ads.length;
    ads[currentAdIndex].style.display = "block";
}

setInterval(switchAd, 3000);

// Update subcategories dynamically
function updateSubCategory() {
    const mainCategory = document.getElementById("mainCategory").value;
    const subCategory = document.getElementById("subCategory");

    subCategory.innerHTML = ""; // Clear previous options

    if (mainCategory === "electronics") {
        subCategory.innerHTML = `
            <option value="mobile">Mobile Phones</option>
            <option value="laptop">Laptops</option>
            <option value="tv">Television</option>
        `;
    } else if (mainCategory === "fashion") {
        subCategory.innerHTML = `
            <option value="clothing">Clothing</option>
            <option value="shoes">Shoes</option>
        `;
    } else if (mainCategory === "home") {
        subCategory.innerHTML = `
            <option value="furniture">Furniture</option>
            <option value="kitchen">Kitchen Appliances</option>
        `;
    }
}
