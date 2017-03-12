(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('EjecucionPruebaDialogController', EjecucionPruebaDialogController);

    EjecucionPruebaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EjecucionPrueba', 'Prueba'];

    function EjecucionPruebaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EjecucionPrueba, Prueba) {
        var vm = this;

        vm.ejecucionPrueba = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.pruebas = Prueba.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ejecucionPrueba.id !== null) {
                EjecucionPrueba.update(vm.ejecucionPrueba, onSaveSuccess, onSaveError);
            } else {
                EjecucionPrueba.save(vm.ejecucionPrueba, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:ejecucionPruebaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
