(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoAlertaDetailController', TipoAlertaDetailController);

    TipoAlertaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoAlerta', 'Alerta'];

    function TipoAlertaDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoAlerta, Alerta) {
        var vm = this;

        vm.tipoAlerta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:tipoAlertaUpdate', function(event, result) {
            vm.tipoAlerta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
