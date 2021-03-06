(function() {
    'use strict';
    angular
        .module('smartTestUiApp')
        .factory('EjecucionPrueba', EjecucionPrueba);

    EjecucionPrueba.$inject = ['$resource', 'DateUtils'];

    function EjecucionPrueba ($resource, DateUtils) {
        var resourceUrl =  'api/ejecucion-pruebas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fecha = DateUtils.convertLocalDateToServer(copy.fecha);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fecha = DateUtils.convertLocalDateToServer(copy.fecha);
                    return angular.toJson(copy);
                }
            },

            'ejecutarPrueba': {
                method: 'POST',
                url : 'api/ejecucion-pruebas/execPrueba'
            }


        });
    }
})();
