import axios from 'axios'

function checkAuth(path) {

    let menuList = JSON.parse(localStorage.getItem('auth'));

    return hasAuth(menuList, path);
}

function downloadExcel(api, params, fileName) {
    axios({
        method: 'get',
        url: api,
        params: params,
        responseType: 'blob'
    }).then(res => {
        // const filename = decodeURI(res.headers['content-disposition'].split(';')[1].split('=')[1]) || 'template.xls'
        const blob = new Blob([res], {
            type: 'application/octet-stream'
        });
        let url = window.URL.createObjectURL(blob);
        let link = document.createElement('a');
        link.style.display = 'none';
        link.href = url;
        link.setAttribute('download', fileName + '.xls');
        document.body.appendChild(link);
        link.click()
    })
}

function hasAuth(array, path) {

    let result = false;

    array.forEach((item, i) => {
        if (item.path === path) {
            if (item.hasAuth) {
                result = true
            }
        }
        if (!result) {
            if (item.child != null && item.child.length > 0) {
                if (hasAuth(item.child, path)) {
                    result = true;
                }
            }
        }

    });

    return result;
}

export default {
    checkAuth, downloadExcel
}
