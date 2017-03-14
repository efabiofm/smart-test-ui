(function() {
    'use strict';
    angular
        .module('smartTestUiApp')
        .factory('Ambiente', Ambiente);

    Ambiente.$inject = ['$resource'];

    function Ambiente ($resource) {
        var resourceUrl =  'api/ambientes/:id';

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
