import { data, Form, Link, redirect, useNavigation } from "react-router";
import type { Route } from "./+types/login";
import { setAuthToken } from "~/utils/auth";
import { login } from "~/api/auth";
import { Input } from "~/components/ui/input";
import { PasswordInput } from "~/components/auth/password-input";

export default function LoginPage({ actionData }: Route.ComponentProps) {
  const navigation = useNavigation();
  const isSubmitting = navigation.state === "submitting";

  const loginError = actionData?.status === 401 ? actionData.error : null;
  const formErrors = actionData?.status === 400 ? actionData.errors : null;

  return (
    <main className="grid h-screen w-screen place-items-center p-8">
      <section className="max-w-md">
        <Form
          method="post"
          action="/login"
          className="flex flex-col gap-6 rounded border border-gray-300 p-4 shadow-sm"
          replace
        >
          <h2 className="text-center text-3xl font-semibold tracking-tight text-gray-800">
            Flagship
          </h2>
          {loginError !== null && (
            <div className="flex h-8 items-center overflow-hidden rounded-sm">
              <span className="flex h-full w-8 items-center justify-center bg-red-600 text-red-100">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth={1.5}
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  className="size-5"
                >
                  <circle cx="12" cy="12" r="10" />
                  <path d="m15 9-6 6" />
                  <path d="m9 9 6 6" />
                </svg>
              </span>
              <span className="px-2 py-1.5 text-sm tracking-tight text-red-600">
                {loginError !== null ? loginError : ""}
              </span>
            </div>
          )}
          <div className="flex flex-col gap-2">
            <label htmlFor="username" className="tracking-tight text-gray-800">
              Username
            </label>
            <Input
              id="username"
              name="username"
              type="text"
              placeholder="Enter your username"
            />
            {formErrors !== null && "username" in formErrors && (
              <div className="flex flex-row items-center gap-1.5 text-sm tracking-tight text-red-600">
                <span>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="size-4"
                  >
                    <circle cx="12" cy="12" r="10" />
                    <line x1="12" x2="12" y1="8" y2="12" />
                    <line x1="12" x2="12.01" y1="16" y2="16" />
                  </svg>
                </span>

                <span>{formErrors.username}</span>
              </div>
            )}
          </div>
          <div className="flex flex-col gap-2">
            <label htmlFor="password" className="tracking-tight text-gray-800">
              Password
            </label>
            <PasswordInput
              id="password"
              name="password"
              placeholder="Enter your password"
            />
            {formErrors !== null && "password" in formErrors && (
              <div className="flex flex-row items-center gap-1.5 text-sm tracking-tight text-red-600">
                <span>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="size-4"
                  >
                    <circle cx="12" cy="12" r="10" />
                    <line x1="12" x2="12" y1="8" y2="12" />
                    <line x1="12" x2="12.01" y1="16" y2="16" />
                  </svg>
                </span>

                <span>{formErrors.password}</span>
              </div>
            )}
          </div>
          <div className="space-y-4">
            <button
              type="submit"
              disabled={isSubmitting}
              className="flex w-full cursor-pointer flex-row items-center justify-center gap-2 rounded bg-orange-500 px-4 py-2 tracking-tight text-white transition-colors not-disabled:hover:bg-orange-400 not-disabled:active:bg-orange-600 disabled:cursor-default disabled:opacity-50"
            >
              {isSubmitting ? (
                <>
                  <span>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 24 24"
                      fill="none"
                      stroke="currentColor"
                      strokeWidth={2}
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      className="size-5 animate-spin"
                    >
                      <path d="M21 12a9 9 0 1 1-6.219-8.56" />
                    </svg>
                  </span>
                  <span>Logging in...</span>
                </>
              ) : (
                <span>Login</span>
              )}
            </button>
            <div className="flex flex-row justify-center gap-1 text-center text-sm tracking-tight text-gray-800">
              <p>{"Don't have an account?"}</p>
              <Link
                className="text-orange-500 hover:text-orange-400 active:text-orange-600"
                to="/signup"
              >
                Sign up
              </Link>
            </div>
          </div>
        </Form>
      </section>
    </main>
  );
}

export async function action({ request }: Route.ActionArgs) {
  const formData = await request.formData();
  const formErrors: Record<string, string> = {};
  let hasFormErrors = false;

  const usernameEntry = formData.get("username");
  if (usernameEntry === null) {
    formErrors.username = "Username is missing";
    hasFormErrors = true;
  }

  const passwordEntry = formData.get("password");
  if (passwordEntry === null) {
    formErrors.password = "Password is missing";
    hasFormErrors = true;
  }

  if (hasFormErrors) {
    return data(
      { ok: false, status: 400 as const, errors: formErrors },
      { status: 400 }
    );
  }

  const username = usernameEntry?.toString() ?? "";
  const password = passwordEntry?.toString() ?? "";

  const result = await login({ username, password });

  if (!result.ok) {
    return data(
      {
        ok: false,
        status: 401 as const,
        error: "Invalid username or password",
      },
      { status: 401 }
    );
  }

  const { token } = await result.json();
  return data({ ok: true, status: 200 as const, token }, { status: 200 });
}

export async function clientAction({ serverAction }: Route.ClientActionArgs) {
  const response = await serverAction();
  if (response.status === 200) {
    setAuthToken(response.token);
    throw redirect("/projects");
  } else {
    return response;
  }
}
