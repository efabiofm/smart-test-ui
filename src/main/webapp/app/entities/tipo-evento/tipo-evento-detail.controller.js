(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoEventoDetailController', TipoEventoDetailController);

    TipoEventoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoEvento', 'Bitacora'];

    function TipoEventoDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoEvento, Bitacora) {
        var vm = this;

        vm.tipoEvento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartTestUiApp:tipoEventoUpdate', function(event, result) {
            vm.tipoEvento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
