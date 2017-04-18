(function() {
    'use strict';
    angular
        .module('smartTestUiApp')
        .factory('Prueba', Prueba);

    Prueba.$inject = ['$resource'];

    function Prueba ($resource) {
        var resourceUrl =  'api/pruebas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: '',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },

            'getURI': {
                method: 'GET',
                url : 'api/pruebas/obtUri/:id',
                transformResponse:function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }

        });
    }
})();
