/* Costanti */

const apiUrl = 'http://localhost:8080/api/v1/photos'
const root = document.getElementById('root')
const searchButton = document.getElementById('searchButton')
const messageForm = document.getElementById('messageForm')

// Funzione che renderizza le categorie

const renderCategories = (categories) => {
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

// Funzione che controlla se il form di ricerca viene invocato

searchButton.addEventListener('click', (event) => {
  event.preventDefault()
  const value = search.value.trim()
  imageFilter(value)
})

// funzione che effettua una ricerca delle foto in base al parametro inserito dall'utente

async function imageFilter(search) {
  const request = apiUrl + '?search=' + search

  try {
    const response = await axios.get(request)
    renderPhotoList(response.data)
  } catch (error) {
    console.log(error)
  }
}

// Funzione che controlla se il form per il messaggio è stavo inviato

messageForm.addEventListener('click', (event) => {
  event.preventDefault()
  const userEmail = email.value
  const userMessage = message.value
  savemessage(userEmail, userMessage)
  email.value = ''
  message.value = ''
})

// Funzione che invia il messaggio al database

function savemessage(userEmail, userMessage) {
  const message = {
    email: userEmail,
    message: userMessage,
  }

  fetch(apiUrl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(message),
  })
    .then((response) => {
      if (response.status === 200) {
        window.alert('Message sent correctly')
      } else {
        window.alert('Error sending message! Try again')
      }
    })
    .catch((error) => {
      window.alert('Error sending message! Try again')
    })
}

/* Codice globale eseguito al load dello script */

getPhotos()
