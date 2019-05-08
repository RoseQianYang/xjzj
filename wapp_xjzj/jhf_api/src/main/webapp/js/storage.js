const sessionStorage = window.sessionStorage

function setStorage(key, value) {
  sessionStorage.setItem(key, JSON.stringify(value))
}

function getStorage(key) {
  var value = sessionStorage.getItem(key)
  var val = ''
  try {
    val = JSON.parse(value)
  } catch (e) {
    val = value
  }
  return val
}

function removeStorage(key) {
  if (key) {
    sessionStorage.removeItem(key)
  } else {
    sessionStorage.clear()
  }
}
