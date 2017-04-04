(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('SeguridadDetailController', SeguridadDetailController);

    SeguridadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Seguridad'];

    function SeguridadDetailController($scope, $rootScope, $stateParams, previousState, entity, Seguridad) {
        var vm = this;

        vm.seguridad = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:seguridadUpdate', function(event, result) {
            vm.seguridad = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
