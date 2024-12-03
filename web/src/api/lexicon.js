import request from "@/utils/request";

export function pageQuery(page, limit, data) {
  return request({
    url: `/lexicon/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/lexicon/deleteById/${id}`,
    method: 'delete'
  })
}

export function insert(data) {
  return request({
    url: `/lexicon/insert`,
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: `/lexicon/update`,
    method: 'put',
    data: data
  })
}
export function getLexiconAll() {
  return request({
    url: `/lexicon/getLexiconAll`,
    method: 'get'
  })
}
