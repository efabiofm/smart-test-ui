(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('service-provider', {
            parent: 'entity',
            url: '/service-provider',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.serviceProvider.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/service-provider/service-providers.html',
                    controller: 'ServiceProviderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('serviceProvider');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('service-provider-detail', {
            parent: 'entity',
            url: '/service-provider/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.serviceProvider.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/service-provider/service-provider-detail.html',
                    controller: 'ServiceProviderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('serviceProvider');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ServiceProvider', function($stateParams, ServiceProvider) {
                    return ServiceProvider.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'service-provider',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('service-provider-detail.edit', {
            parent: 'service-provider-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-provider/service-provider-dialog.html',
                    controller: 'ServiceProviderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServiceProvider', function(ServiceProvider) {
                            return ServiceProvider.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('service-provider.new', {
            parent: 'service-provider',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-provider/service-provider-dialog.html',
                    controller: 'ServiceProviderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                serviceProviderId: null,
                                nombre: null,
                                activo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('service-provider', null, { reload: 'service-provider' });
                }, function() {
                    $state.go('service-provider');
                });
            }]
        })
        .state('service-provider.edit', {
            parent: 'service-provider',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-provider/service-provider-dialog.html',
                    controller: 'ServiceProviderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServiceProvider', function(ServiceProvider) {
                            return ServiceProvider.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('service-provider', null, { reload: 'service-provider' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('service-provider.delete', {
            parent: 'service-provider',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-provider/service-provider-delete-dialog.html',
                    controller: 'ServiceProviderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ServiceProvider', function(ServiceProvider) {
                            return ServiceProvider.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('service-provider', null, { reload: 'service-provider' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
