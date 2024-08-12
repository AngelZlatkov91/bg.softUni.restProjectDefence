let reloadBooksButton = document.getElementById('loadProducts');

reloadBooksButton.addEventListener('click', loadProducts)

function loadProducts() {

    let productsContainer = document.getElementById('products-container');
    productsContainer.innerHTML = ''

    fetch('http://localhost:8080/api/products/')
        .then(response => response.json())
        .then(json => json.forEach(product => {

            let productRow = document.createElement('tr')

            let nameProduct = document.createElement('th')
            let brandProduct	= document.createElement('th')
            let typeProduct	= document.createElement('th')
            let priceProduct= document.createElement('th')
            let descriptionProduct= document.createElement('th')

            nameProduct.textContent = product.name
            brandProduct.textContent = product.brand
            typeProduct.textContent = product.type
            priceProduct.textContent = product.price
            descriptionProduct.textContent = product.description

            let deleteProducts = document.createElement('button')
            deleteProducts.innerHTML = 'DELETE'
            deleteProducts.dataset.id = product.id
            deleteProducts.addEventListener('click', deleteBtnClicked)
            descriptionProduct.appendChild(deleteProducts)
            productRow.appendChild(nameProduct)
            productRow.appendChild(brandProduct)
            productRow.appendChild(typeProduct)
            productRow.appendChild(priceProduct)
            productRow.appendChild(descriptionProduct)
            productsContainer.append(productRow)
        }))
}

function deleteBtnClicked(event) {

    let productsId = event.target.dataset.id;
    let requestOptions = {
        method: 'DELETE'
    }

    fetch(`http://localhost:8080/api/products/remove/${productsId}`, requestOptions)
        .then(_ => loadProducts())
        .catch(error => console.log('error', error))
}