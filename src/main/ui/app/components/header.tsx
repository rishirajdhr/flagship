import { Form } from "react-router";

export default function Header(props: { username: string }) {
  return (
    <header className="flex w-full flex-row items-center justify-between border-b border-gray-200 p-4 shadow-2xs">
      <div className="text-xl font-semibold tracking-tight text-gray-800">
        Flagship
      </div>
      <div className="flex flex-row items-center gap-4">
        <div className="text-sm tracking-tight text-gray-700 italic">
          Logged in as {props.username}
        </div>
        <Form method="POST" action="/logout">
          <button className="flex flex-row items-center gap-1 rounded-sm px-4 py-2 text-sm tracking-tight text-gray-700 transition-colors hover:bg-red-100 hover:text-red-700 active:bg-red-50">
            <span>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
                className="size-6"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15m3 0 3-3m0 0-3-3m3 3H9"
                />
              </svg>
            </span>
            <span>Log out</span>
          </button>
        </Form>
      </div>
    </header>
  );
}
