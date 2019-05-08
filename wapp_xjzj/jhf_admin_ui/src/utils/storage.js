const sessionStorage = window.sessionStorage

export function setStorage(key, value) {
  sessionStorage.setItem(key, JSON.stringify(value))
}

export function getStorage(key) {
  let value = sessionStorage.getItem(key)
  let val = ''
  try {
    val = JSON.parse(value)
  } catch (e) {
    val = value
  }
  return val
}

export function removeStorage(key) {
  if (key) {
    sessionStorage.removeItem(key)
  } else {
    sessionStorage.clear()
  }
}
