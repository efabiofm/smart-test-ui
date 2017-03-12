(function() {
    'use strict';
    angular
        .module('smartTestUiApp')
        .factory('Parametro', Parametro);

    Parametro.$inject = ['$resource'];

    function Parametro ($resource) {
        var resourceUrl =  'api/parametros/:id';

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
