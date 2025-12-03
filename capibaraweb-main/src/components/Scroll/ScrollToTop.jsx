import { useEffect } from "react";
import { useLocation } from "react-router-dom";

export default function ScrollToTop() {
  const { pathname } = useLocation();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);

  return null;
}

/**
 * @fileoverview Scroll restoration component for route navigation
 * 
 * @description
 * This utility component automatically scrolls the browser window to the top (0,0)
 * whenever the route pathname changes. It's designed to be used at the root level
 * of the application to ensure consistent scroll behavior across route transitions,
 * preventing the page from maintaining scroll position when navigating between routes.
 * 
 * @component ScrollToTop
 * @returns {null} This component renders nothing (null) and only provides side effects
 * 
 * @hook {useLocation} pathname - Extracts the current pathname from react-router
 * @hook {useEffect} - Triggers scroll reset when pathname changes
 * 
 * @sideEffects
 * - Scrolls the window to coordinates (0, 0) on every route change
 * - Effect runs after every pathname change
 * 
 * @dependencies
 * - react: Core React library with hooks (useEffect)
 * - react-router-dom: Routing library for location tracking (useLocation)
 * 
 * @usage
 * This component should be placed inside a Router context, typically in the root
 * of your application alongside route definitions.
 * 
 * @example
 * // Typical usage in App component
 * import { BrowserRouter } from 'react-router-dom';
 * import ScrollToTop from './components/Scroll/ScrollToTop';
 * import AppRoutes from './routes/AppRoutes';
 * 
 * function App() {
 *   return (
 *     <BrowserRouter>
 *       <ScrollToTop />
 *       <AppRoutes />
 *     </BrowserRouter>
 *   );
 * }
 * 
 * @author Rooftop Development Team
 * @version 1.0.0
 */

