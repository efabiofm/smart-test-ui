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
                        data.fecha = DateUtils.convertLocalDateFromServer(data.fecha);
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
                method: 'GET',
                url : 'api/ejecucion-prueba/execPrueba/:id',
                transformRequest: function (data) {
                    return {data : data};
                }
            }


        });
    }
})();
