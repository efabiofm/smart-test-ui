(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('AlertaDetailController', AlertaDetailController);

    AlertaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Alerta', 'TipoAlerta'];

    function AlertaDetailController($scope, $rootScope, $stateParams, previousState, entity, Alerta, TipoAlerta) {
        var vm = this;

        vm.alerta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:alertaUpdate', function(event, result) {
            vm.alerta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
