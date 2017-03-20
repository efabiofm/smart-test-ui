(function() {
    'use strict';
    angular
        .module('smartTestUiApp')
        .factory('TipoHeader', TipoHeader);

    TipoHeader.$inject = ['$resource'];

    function TipoHeader ($resource) {
        var resourceUrl =  'api/tipo-headers/:id';

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
