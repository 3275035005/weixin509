import request from "@/utils/request";

export function pageQuery(page, limit, data) {
  return request({
    url: `/magazine/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/magazine/deleteById/${id}`,
    method: 'delete'
  })
}

export function insert(data) {
  return request({
    url: `/magazine/insert`,
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: `/magazine/update`,
    method: 'put',
    data: data
  })
}
