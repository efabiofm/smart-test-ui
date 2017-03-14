(function() {
    'use strict';
    angular
        .module('smartTestUiApp')
        .factory('TipoParametro', TipoParametro);

    TipoParametro.$inject = ['$resource'];

    function TipoParametro ($resource) {
        var resourceUrl =  'api/tipo-parametros/:id';

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
