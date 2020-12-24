import request from '../../utils/request';

export const queryZtreeUmsMenu =() => {
    return request({
        url: '/yh_shop/umsMenuController/queryZtreeUmsMenu',
        method: 'get',
    });
};
export const queryUmsMenu =(query) => {
    return request({
        url: '/yh_shop/umsMenuController/queryUmsMenu',
        method: 'get',
        params:query,
    });
};
export const queryUmsRoleIdMenu = id => {
    return request({
        url: '/yh_shop/umsRoleMenuController/queryUmsRoleIdMenu',
        method: 'get',
        params:{id:id},
    });
};
export const queryUmsMenuById = id => {
    return request({
        url: '/yh_shop/umsMenuController/queryUmsMenuById',
        method: 'get',
        params: {id:id}
    });
}
export const saveUmsRoleIdMenu =list => {
    return request({
        url: '/yh_shop/umsRoleMenuController/saveUmsRoleIdMenu',
        method: 'post',
        data:list,
    });
};





export const deleteUmsMenu =(id) => {
    return request({
        url: '/yh_shop/umsMenuController/deleteUmsMenu',
        method: 'delete',
        params:{id:id},
    });
};

export const updateUmsMenu =(query) => {
    return request({
        url: '/yh_shop/umsMenuController/updateUmsMenu',
        method: 'put',
        params:query,
    });
};
export const saveUmsMenu =(query) => {
    return request({
        url: '/yh_shop/umsMenuController/saveUmsMenu',
        method: 'post',
        params:query,
    });
};