import request from "@/utils/request";

export function pageQuery(page, limit, data) {
  return request({
    url: `/feedback/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}
export function deleteById(id) {
  return request({
    url: `/feedback/deleteById/${id}`,
    method: 'delete'
  })
}

export function reply(data) {
  return request({
    url: `/feedback/reply`,
    method: 'put',
    data: data
  })
}

export function sendFeedback(data) {
  return request({
    url: `/feedback/sendFeedback`,
    method: 'post',
    data: data
  })
}
