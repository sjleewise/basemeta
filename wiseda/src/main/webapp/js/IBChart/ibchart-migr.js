/**
 * 오브젝트의 키들을 camelCase로 변환
 */
function toCamel(o) {
  var newO, origKey, newKey, value
  if (o instanceof Array) {
    return o.map(function(value) {
        if (typeof value === "object") {
          value = toCamel(value)
        }
        return value
    })
  } else {
    newO = {}
    for (origKey in o) {
      if (o.hasOwnProperty(origKey)) {
        newKey = (origKey.charAt(0).toLowerCase() + origKey.slice(1) || origKey).toString()
        value = o[origKey]
        if (value instanceof Array || (value != null && value.constructor === Object)) {
          value = toCamel(value)
        }
        newO[newKey] = value
      }
    }
  }
  return newO
}

function getChartAxisOptions(chart, dir, ndx, prop) {
  if (chart == null) return
  var opts = chart.getOptions()
  var axisOpts = opts[dir + 'Axis']
  if (axisOpts == null) return
  if (ndx == null) ndx = 0
  if (axisOpts instanceof Array) {
    axisOpts = axisOpts[ndx]
  }
  if (prop == null) return axisOpts
  return axisOpts[prop] || undefined
}

/**
 * IBChartLite Migration Wrapper for IBChart v7.2
 * Company: IB Leaders
 * Author: 김의연
 * Email: eykim@ibleaders.co.kr
 * Release: 2020. 1. 7
 */
// (function(ibchartlite) {
//   if (ibchartlite == null) return
//   console.log('ibchartlite migr', ibchartlite)
// })(window["IBChartLiteCore"])
