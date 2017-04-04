(function() {
    'use strict';
    angular
        .module('smartTestUiApp')
        .factory('Modulo', Modulo);

    Modulo.$inject = ['$resource'];

    function Modulo ($resource) {
        var resourceUrl =  'api/modulos/:id';

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
