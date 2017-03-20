(function() {
    'use strict';
    angular
        .module('smartTestUiApp')
        .factory('Alerta', Alerta);

    Alerta.$inject = ['$resource'];

    function Alerta ($resource) {
        var resourceUrl =  'api/alertas/:id';

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
            'update': { method:'PUT' }
        });
    }
})();
