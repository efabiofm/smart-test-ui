(function() {
    'use strict';
    angular
        .module('smartTestUiApp')
        .factory('TipoEvento', TipoEvento);

    TipoEvento.$inject = ['$resource'];

    function TipoEvento ($resource) {
        var resourceUrl =  'api/tipo-eventos/:id';

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
