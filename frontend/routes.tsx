import {protectRoutes} from '@hilla/react-auth';
import HomeView from 'Frontend/views/home/HomeView';
import LoginView from 'Frontend/views/login/LoginView.js';
import MainLayout from 'Frontend/views/MainLayout.js';
import {lazy} from 'react';
import {createBrowserRouter, RouteObject} from 'react-router-dom';
import SignUpView from "Frontend/views/signUp/SignUpView";
import TradingView from "Frontend/views/trading/tradingView";

const AboutView = lazy(async () => import('Frontend/views/trading/tradingView'));

export const routes = protectRoutes([
    {path: '/', element: <SignUpView/>},
    {path: '/login', element: <LoginView/>},
    {
        element: <MainLayout/>,
        handle: {title: 'Main'},
        children: [
            {path: '/home', element: <HomeView/>, handle: {title: 'Home', requiresLogin: true}},
            {path: '/trade', element: <TradingView/>, handle: {title: 'Trade', requiresLogin: true}},
        ],
    },


]) as RouteObject[];

export default createBrowserRouter(routes);
