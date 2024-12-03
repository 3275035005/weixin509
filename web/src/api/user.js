import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/sys-user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/sys-user/info',
    method: 'get',
    params: { token }
  })
}

export function pageQuery(page, limit, data) {
  return request({
    url: `/sys-user/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}
export function deleteById(id) {
  return request({
    url: `/sys-user/deleteById/${id}`,
    method: 'delete'
  })
}
export function insert(data){
  return request({
    url: `/sys-user/insert`,
    method: 'post',
    data: data
  })
}

export function update(data){
  return request({
    url: `/sys-user/update`,
    method: 'put',
    data: data
  })
}
export function getUserAll() {
  return request({
    url: '/sys-user/getUserAll',
    method: 'get'
  })
}
export function getHome() {
  return request({
    url: '/sys-user/getHome',
    method: 'get'
  })
}

export function updatePassword(data){
  return request({
    url: `/sys-user/updatePassword`,
    method: 'post',
    data: data
  })
}

export function resetPassword(id){
  return request({
    url: `/sys-user/resetPassword/`+id,
    method: 'post'
  })
}

