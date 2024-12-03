import request from "@/utils/request";

export function pageQuery(page, limit, data) {
  return request({
    url: `/lexicon-info/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/lexicon-info/deleteById/${id}`,
    method: 'delete'
  })
}

export function insert(data) {
  return request({
    url: `/lexicon-info/insert`,
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: `/lexicon-info/update`,
    method: 'put',
    data: data
  })
}
