(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('BitacoraDetailController', BitacoraDetailController);

    BitacoraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Bitacora', 'TipoEvento'];

    function BitacoraDetailController($scope, $rootScope, $stateParams, previousState, entity, Bitacora, TipoEvento) {
        var vm = this;

        vm.bitacora = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:bitacoraUpdate', function(event, result) {
            vm.bitacora = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
