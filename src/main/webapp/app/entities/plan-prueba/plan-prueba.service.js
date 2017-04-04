(function() {
    'use strict';
    angular
        .module('smartTestUiApp')
        .factory('PlanPrueba', PlanPrueba);

    PlanPrueba.$inject = ['$resource'];

    function PlanPrueba ($resource) {
        var resourceUrl =  'api/plan-pruebas/:id';

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
