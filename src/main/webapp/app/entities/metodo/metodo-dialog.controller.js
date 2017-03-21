(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('MetodoDialogController', MetodoDialogController);

    MetodoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Metodo', 'Servicio', 'Prueba', 'Parametro'];

    function MetodoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Metodo, Servicio, Prueba, Parametro) {
        var vm = this;

        vm.metodo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.servicios = Servicio.query();
        vm.pruebas = Prueba.query();
        vm.parametros = Parametro.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.metodo.id !== null) {
                Metodo.update(vm.metodo, onSaveSuccess, onSaveError);
            } else {
                Metodo.save(vm.metodo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:metodoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
