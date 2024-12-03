import request from "@/utils/request";

export function pageQuery(page, limit, data) {
  return request({
    url: `/magazine-classified/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/magazine-classified/deleteById/${id}`,
    method: 'delete'
  })
}

export function insert(data) {
  return request({
    url: `/magazine-classified/insert`,
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: `/magazine-classified/update`,
    method: 'put',
    data: data
  })
}

export function getClassifiedAll() {
  return request({
    url: `/magazine-classified/getClassifiedAll`,
    method: 'get'
  })
}
