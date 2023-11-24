/* Costanti */

const apiUrl = 'http://localhost:8080/api/v1/photos'
const root = document.getElementById('root')

// Funzione che renderizza le categorie

const renderCategories = (categories) => {
  console.log(categories)
  let content

  if (categories.length === 0) {
    content = 'No categories'
  } else {
    content = '<ul class="list-unstyled">'
    categories.forEach((cat) => {
      content += `<li>${cat.name}</li>`
    })
  }
  return content
}

// Funzione che renderizza la card del book

const renderPhoto = (element) => {
  const imgUrl = apiUrl + '/' + element.id

  return `<div class="card shadow h-100">
  <img src=${imgUrl}>
   <div class=card-body>
    <h5 class="">${element.title}</h5>
    <p>${element.description}</p>
   </div>
   <div class="card-footer">
   ${renderCategories(element.categories)}
   </div>
  </div>`
}

// Funzione che renderizza la gallery di card

const renderPhotoList = (data) => {
  let content

  if (data.length > 0) {
    // Creo la gallery

    content = '<div class="row">'

    // Itero sull'array di libri

    data.forEach((element) => {
      content += '<div class= col-3>'

      // Chiamo il metodo che restituisce il singolo book

      content += renderPhoto(element)
      content += '</div>'
    })

    content += '</div>'
  } else {
    content = '<div class="alert alert-info">The list is empty</div>'
  }

  // Sostituisco il contenuto di root con il content

  root.innerHTML = content
}

// Funzione che chiama l'API e ottiene l'array di foto

const getPhotos = async () => {
  try {
    // Prendo i dati dall'API

    const response = await axios.get(apiUrl)

    // Passo i dati alla funzione per renderizzarli
    renderPhotoList(response.data)
  } catch (error) {
    console.log(error)
  }
}

/* Codice globale eseguito al load dello script */

getPhotos()
