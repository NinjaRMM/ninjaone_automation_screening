export const capitalize = s => {
  if (typeof s !== 'string') return ''
  return s.replace(/\w\S*/g, function (txt) {
    return txt.charAt(0).toUpperCase() + txt.substring(1).toLowerCase()
  })
}